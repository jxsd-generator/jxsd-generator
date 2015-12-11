package org.jxsd.generator.singleton;

import static java.util.Calendar.getInstance;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.jxsd.generator.XSDConstants.APP_CONFIG_FILE;
import static org.jxsd.generator.XSDConstants.CSV_FILE;
import static org.jxsd.generator.XSDConstants.KEY_DIR_IN;
import static org.jxsd.generator.XSDConstants.KEY_DIR_OUT;
import static org.jxsd.generator.XSDConstants.XSD_FORMAT_INT;
import static org.jxsd.generator.utils.DateUtils.FORMAT_DATE_HOUR;
import static org.jxsd.generator.utils.DateUtils.calendarToString;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.mutable.MutableInt;

/**
 * Main singleton.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
@Singleton
public class MainSingleton {
    @Inject
    private SimpleLogger LOGGER;

    @Inject
    private PropertyReader propReader;

    @Inject
    private CSVLoader loader;

    @Inject
    private XSDTemplateReader tplReader;

    @Inject
    private Transcoding transco;

    @Inject
    private FileUtils fileUtils;

    private String version = "0";

    /**
     * Traitement.
     * 
     * @param pathInDir
     * @param pathOutDir
     * @throws IOException
     */
    public void run() throws IOException {
        String pathInDir = propReader.get(APP_CONFIG_FILE, KEY_DIR_IN);
        String pathOutDir = propReader.get(APP_CONFIG_FILE, KEY_DIR_OUT);
        LOGGER.info("MainSingleton", "Scanning " + pathInDir);

        File dirIn = new File(pathInDir);
        File[] children = dirIn.listFiles();

        String generationDate = calendarToString(getInstance(), FORMAT_DATE_HOUR);

        if (null == children || children.length <= 0) {
            LOGGER.info("MainSingleton|run", "There's no CSV file to process...");
            return;
        }

        for (File child : children) {
            String nameElem = child.getName();

            if (nameElem.equalsIgnoreCase("VERSION") || nameElem.equalsIgnoreCase("VERSION.txt")) {
                version = fileUtils.file2stringQuietly(new FileInputStream(child));
                break;
            }
        }

        for (File child : children) {
            String nameElem = child.getName();

            if (!nameElem.toLowerCase().matches(CSV_FILE)) {
                continue;
            }

            LOGGER.info("MainSingleton|run", "Processing " + nameElem);
            CSVParser parser = loader.load(child);

            StringJoiner content = new StringJoiner("\r\n\t\t");
            StringJoiner includes = new StringJoiner("\r\n\t\t");
            StringJoiner namespace = new StringJoiner("\r\n");
            MutableInt i = new MutableInt(0);
            String root = FilenameUtils.removeExtension(child.getName());
            parser.forEach(r -> processRecord(root, r, content, includes, namespace, i, version));

            fileUtils.string2fileQuietly(pathOutDir + File.separator + "binding.xjb", tplReader.getXjbBindingTemplate());

            Map<String, String> replacements = new HashMap<String, String>();
            replacements.put("INCLUDES", includes.toString());
            replacements.put("ELEMENTS", content.toString());
            replacements.put("NS", root.toLowerCase());
            replacements.put("NAME", root.toUpperCase());
            replacements.put("NAMESPACES", namespace.toString());
            replacements.put("VERSION", version);
            replacements.put("GENERATION_DATE", generationDate);

            String finalContent = tplReader.format(tplReader.getXsdMainTemplate(), replacements);

            fileUtils.string2fileQuietly(pathOutDir + File.separator + root + ".xsd", finalContent);

            parser.close();
        }
    }

    /**
     * Traiter un enregistrement.
     * 
     * @param r
     */
    private void processRecord(String root, CSVRecord r, StringJoiner content, StringJoiner includes, StringJoiner namespace, MutableInt i, String version) {
        // On saute l'entête
        if (i.getValue() == 0) {
            i.add(1);
            return;
        }

        String type = null;
        String nom = r.get("Name").toLowerCase().trim();
        String format = (isNotEmpty(r.get("Format"))) ? r.get("Format").toUpperCase().trim() : nom;
        if (!transco.getCsvToXsdTypes().containsKey(format)) {
            type = String.format("%s:%s", nom.toLowerCase(), nom.toUpperCase());

            Map<String, String> repInc = new HashMap<String, String>();
            repInc.put("NAME", nom.toUpperCase().trim());
            repInc.put("SCHEMA", nom);
            repInc.put("NS", nom.toLowerCase().trim());
            repInc.put("VERSION", version);

            includes.add(tplReader.format(tplReader.getXsdIncludeTemplate(), repInc));
            namespace.add(tplReader.format(tplReader.getXsdNamespaceTemplate(), repInc));
        } else {
            type = transco.getCsvToXsdTypes().get(format.toUpperCase());
        }

        String elem = null;
        String lg = r.get("Length");
        String nullable = r.get("Nullable");
        if (!"true".equalsIgnoreCase(nullable.trim())) {
            nullable = "false";
        }

        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("NULLABLE", nullable.trim().toLowerCase());
        replacements.put("NAME_ELEMENT", r.get("Name").trim());
        replacements.put("TYPE_ELEMENT", type);
        replacements.put("DESC_ELEMENT", r.get("Description"));
        replacements.put("MIN_OCCURS", (isEmpty(r.get("Min")) || !isNumeric(r.get("Min").trim())) ? "0" : r.get("Min").trim());
        replacements.put("MAX_OCCURS", (isEmpty(r.get("Max")) || !isNumeric(r.get("Max").trim())) ? "unbounded" : r.get("Max").trim());
        replacements.put("VERSION", version);

        List<String> values = new ArrayList<String>();
        String strValues = r.get("Values");

        if (isNotEmpty(strValues) && strValues.contains("|")) {
            Arrays.asList(strValues.split("\\|")).stream().filter(item -> isNotBlank(item)).forEach(item -> values.add(item.trim()));
        } else if (isNotBlank(strValues)) {
            values.add(strValues);
        }

        if ((isNotEmpty(lg) && !XSD_FORMAT_INT.equalsIgnoreCase(type)) || isNotEmpty(values)) {
            replacements.put("MAX_LG", lg);
            replacements.put("RESTRICTION_ENUM", "");
            replacements.put("RESTRICTION_LG", "");

            if (isNotEmpty(values)) {
                StringJoiner joiner = new StringJoiner("\r\n\t\t\t");

                for (String val : values) {
                    Map<String, String> replacements2 = new HashMap<String, String>();
                    replacements2.put("VALUES", val);
                    joiner.add(tplReader.format(tplReader.getXsdEnumTemplate(), replacements2));
                }

                replacements.put("RESTRICTION_ENUM", joiner.toString());
            }

            // Pas de longueur pour les int
            if (isNotEmpty(lg) && !XSD_FORMAT_INT.equalsIgnoreCase(type)) {
                String lngFragment = tplReader.format(tplReader.getXsdLgTemplate(), replacements);
                replacements.put("RESTRICTION_LG", lngFragment);
            }

            elem = tplReader.format(tplReader.getXsdElementTemplate(), replacements);
        } else {
            elem = tplReader.format(tplReader.getXsdElementNoRestrictionTemplate(), replacements);
        }

        content.add(elem);
        i.add(1);
    }
}

package org.jxsd.generator.singleton;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Loading templates.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
@Singleton
public class XSDTemplateReader {
	@Inject
	private FileUtils fileUtils;

	private String xsdNamespaceTemplate;
	private String xsdElementNoRestrictionTemplate;
	private String xsdElementTemplate;
	private String xsdIncludeTemplate;
	private String xsdMainTemplate;
	private String xsdLgTemplate;
	private String xsdEnumTemplate;
	private String xjbBindingTemplate;

	/**
	 * Init.
	 */
	@PostConstruct
	public void init() {
		String PATH_TEMPLATE = "templates/";
		xsdElementTemplate = fileUtils.file2stringQuietly(getClass().getResourceAsStream(PATH_TEMPLATE + "xsd_element_template.xml"));
		xsdIncludeTemplate = fileUtils.file2stringQuietly(getClass().getResourceAsStream(PATH_TEMPLATE + "xsd_include_template.xml"));
		xsdMainTemplate = fileUtils.file2stringQuietly(getClass().getResourceAsStream(PATH_TEMPLATE + "xsd_main_template.xml"));
		xsdElementNoRestrictionTemplate = fileUtils.file2stringQuietly(getClass().getResourceAsStream(PATH_TEMPLATE + "xsd_element_norestriction_template.xml"));
		xsdNamespaceTemplate = fileUtils.file2stringQuietly(getClass().getResourceAsStream(PATH_TEMPLATE + "xsd_namespace_template.xml"));
		xsdLgTemplate = fileUtils.file2stringQuietly(getClass().getResourceAsStream(PATH_TEMPLATE + "xsd_lg_template.xml"));
		xsdEnumTemplate = fileUtils.file2stringQuietly(getClass().getResourceAsStream(PATH_TEMPLATE + "xsd_enumeration_value_template.xml"));
		xjbBindingTemplate = fileUtils.file2stringQuietly(getClass().getResourceAsStream(PATH_TEMPLATE + "xjb_binding_template.xml"));
	}

	/**
	 * Process replacements.
	 * 
	 * @param tpl
	 * @param replacements
	 * @return String
	 */
	public String format(String tpl, Map<String, String> replacements) {
		String key;
		String rtn = tpl;

		Set<String> keys = replacements.keySet();
		Iterator<String> it = keys.iterator();

		while (it.hasNext()) {
			key = it.next();
			rtn = rtn.replace(String.format("##%s##", key), replacements.get(key));
		}

		return rtn;
	}

	/**
	 * @return the xsdElementTemplate
	 */
	public String getXsdElementTemplate() {
		return xsdElementTemplate;
	}

	/**
	 * @return the xsdIncludeTemplate
	 */
	public String getXsdIncludeTemplate() {
		return xsdIncludeTemplate;
	}

	/**
	 * @return the xsdMainTemplate
	 */
	public String getXsdMainTemplate() {
		return xsdMainTemplate;
	}

	/**
	 * @return the xsdElementNoRestrictionTemplate
	 */
	public String getXsdElementNoRestrictionTemplate() {
		return xsdElementNoRestrictionTemplate;
	}

	/**
	 * @return the xsdNamespaceTemplate
	 */
	public String getXsdNamespaceTemplate() {
		return xsdNamespaceTemplate;
	}

	/**
	 * @return the xsdLongueurTemplate
	 */
	public String getXsdLgTemplate() {
		return xsdLgTemplate;
	}

	/**
	 * @return the xsdEnumTemplate
	 */
	public String getXsdEnumTemplate() {
		return xsdEnumTemplate;
	}

	/**
	 * @return the xjbBindingTemplate
	 */
	public String getXjbBindingTemplate() {
		return xjbBindingTemplate;
	}
}

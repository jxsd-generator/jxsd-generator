package org.jxsd.generator.utils;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapter to parse integers.
 * 
 * @author Idriss Neumann <idriss.neumann@capgemini.com>
 *
 */
public class StringToIntegerAdapter extends XmlAdapter<String, Integer> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Integer unmarshal(String v) throws Exception {
        return isEmpty(v) ? null : Integer.valueOf(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String marshal(Integer v) throws Exception {
        return (null == v) ? null : String.valueOf(v);
    }

}

package org.jxsd.generator;

import java.io.IOException;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jxsd.generator.singleton.MainSingleton;

/**
 * XSD generator main class.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class XSDGenerator {
	/**
	 * Main method.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		WeldContainer weld = new Weld().initialize();
		MainSingleton main = weld.instance().select(MainSingleton.class).get();
		main.run();
	}
}

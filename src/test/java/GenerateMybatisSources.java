import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.internal.ObjectFactory;

import java.net.URL;
import java.util.Arrays;

import static org.mybatis.generator.internal.util.ClassloaderUtility.getCustomClassloader;

public class GenerateMybatisSources {
	public static void main(String[] args) {
//		URL mysqlJar = GenerateMybatisSources.class.getResource("/mysql-connector-java-5.1.42.jar");
//		ClassLoader classLoader = getCustomClassloader(Arrays.asList(mysqlJar.getFile()));
//		ObjectFactory.addExternalClassLoader(classLoader);

		URL url = GenerateMybatisSources.class.getResource("/mybatis-generator.xml");

		ShellRunner.main(new String[] { "-configfile", url.getFile(), "-overwrite" });
	}
}

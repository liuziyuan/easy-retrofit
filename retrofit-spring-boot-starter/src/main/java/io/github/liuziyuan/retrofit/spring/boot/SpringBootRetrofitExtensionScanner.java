package io.github.liuziyuan.retrofit.spring.boot;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class SpringBootRetrofitExtensionScanner {

    private static final String RETROFIT_EXTENSION_PROPERTIES = "META-INF/retrofit-extension.properties";
    private static final String RETROFIT_EXTENSION_CLASS_NAME = "retrofit.extension.name";
    private static final String RETROFIT_BASE_API_PACKAGE = "retrofit.base.api.package";

    /**
     * Scan packageName
     *
     * @return
     * @throws IOException
     */
    public Set<String> scanExtensionName() throws IOException {
        return getClassName(RETROFIT_EXTENSION_CLASS_NAME);
    }

    public Set<String> scanApiPackageName() throws IOException {
        return getClassName(RETROFIT_BASE_API_PACKAGE);
    }

    private static Set<String> getClassName(String name) {
        // 获取类路径下的所有META-INF/spring.factories文件
        Set<String> extensionNames = new HashSet<>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String pattern = "classpath*:" + RETROFIT_EXTENSION_PROPERTIES;
        Resource[] resources = null;
        try {
            resources = resolver.getResources(pattern);
            // 遍历并打印每个文件的内容
            for (Resource resource : resources) {
                if (resource.exists() && resource.isReadable()) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] split = line.split("=");
                            if (name.equalsIgnoreCase(split[0].trim())) {
                                String className = split[1].trim();
                                int lastDotIndex = className.lastIndexOf('.');
                                String packageName = className.substring(0, lastDotIndex);
                                extensionNames.add(packageName);
                            }
                        }
                    }
                }
            }
        } catch (IOException ignored) {
        }
        return extensionNames;
    }


}

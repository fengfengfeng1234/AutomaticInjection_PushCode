package com.example.apt_compiler;

import com.example.apt_annotation.BindView;
import com.example.apt_annotation.RouterPushUri;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;


/**
 * 调试技巧
 * https://juejin.im/entry/6844903617556643854
 */

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"com.example.apt_annotation.RouterPushUri"})
public class BindViewProcessor extends AbstractProcessor {
    private Filer mFilerUtils;       // 文件管理工具类
    private Types mTypesUtils;    // 类型处理工具类
    private Elements mElementsUtils;  // Element处理工具类
    /**
     * 生成类文件路径
     */
    public static final String OPTION_PUSH_PATH = "optionPushPath";

    static HashMap<String, String> allClassName = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        mFilerUtils = processingEnv.getFiler();
        mTypesUtils = processingEnv.getTypeUtils();
        mElementsUtils = processingEnv.getElementUtils();

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {

        if (set == null || set.size() == 0) {
            System.out.println("BindViewProcessor 未使用");
            return false;
        }
        System.out.println("BindViewProcessor 可以使用");
        /**
         * 包名
         * simple
         *   "com.example.apt_demo.PushRouterManager";
         */
        String qualifiedName;
        qualifiedName = processingEnv.getOptions().get(OPTION_PUSH_PATH);
        System.out.println("BindViewProcessor qualifiedName = " + qualifiedName);
        if (qualifiedName == null || qualifiedName.length() == 0) {
            throw new NullPointerException("RouterPushUri   生成文件全路径未指定");
        }

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RouterPushUri.class);

        /**
         * 获取  所有的 className 加 注释
         * https://www.jianshu.com/p/e1382d3df6ed
         */
        for (Element element : elements) {
            //遍历每一个element
            TypeElement classElement = (TypeElement) element;
            RouterPushUri uri = classElement.getAnnotation(RouterPushUri.class);

            /**
             * 获取类全名
             *   com.example.apt_demo.DemoMessage
             *   DemoMessage
             */
            System.out.println("BindViewProcessor  qualifiedNameStr  = " + classElement.getQualifiedName().toString());
            System.out.println("BindViewProcessor qualifiedNameStr  = " + classElement.getSimpleName().toString());

            System.out.println("BindViewProcessor getSuperclass  = " + classElement.getSuperclass().toString());
            /**
             * 打印包信息
             */
            PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(element);
            /**
             * 获取包名
             * com.example.apt_demo
             * apt_demo
             */
            System.out.println("BindViewProcessor qualifiedNameStr  = " + classElement.getQualifiedName().toString());
            System.out.println("BindViewProcessor qualifiedNameStr  = " + classElement.getSimpleName().toString());

            String code = uri.code();
            /**
             * 可做输出一份 code 对应的描述文件
             */
            String codeDescribe = uri.codeDescribe();
            /**
             * 获取包名
             */
            allClassName.put(code, classElement.getQualifiedName().toString());
        }
        BufferedWriter writer = null;

        int lastIndexPoint = qualifiedName.lastIndexOf(".");
        if (lastIndexPoint == -1) {
            throw new NullPointerException("packageName must not  null ||  must  exist point");
        }
        String className = qualifiedName.substring(lastIndexPoint + 1, qualifiedName.length());
        String packageName = qualifiedName.substring(0, lastIndexPoint);

        System.out.println("BindViewProcessor  packageName ====>" + packageName);
        System.out.println("BindViewProcessor  className ====>" + className);
        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(qualifiedName);
            writer = new BufferedWriter(sourceFile.openWriter());
            writer.write("package  " + packageName + " ;\n\n");
            writer.write("import android.util.SparseArray;\n");
            writer.write("import java.lang.reflect.Constructor;\n");
            writer.write("import java.lang.reflect.InvocationTargetException;\n");
            writer.write("import java.util.HashMap;\n");
            writer.write("import java.util.Map;\n");

            for (String key : allClassName.keySet()) {
                String classNamePath = allClassName.get(key);
                writer.write("import " + classNamePath + ";\n");
            }


            writer.write("public class " + className + "  {\n");


            writer.write("  private static " + className + " sInstance;  \n");
            writer.write("  static HashMap<String, String> allClassName= new HashMap<>();  \n");
            writer.write("  static { \n");
            writer.write("        allClassName = new HashMap<>(); \n");
            for (String key : allClassName.keySet()) {
                String classNamePath = allClassName.get(key);
                writer.write("        allClassName.put(\"" + key + "\",\"" + classNamePath + "\"); \n");
            }
            writer.write("   } \n");
            writer.write("  public static PushRouterManager getInstance() { \n");
            writer.write("   if (sInstance == null) { \n");
            writer.write("       synchronized (PushRouterManager.class) { \n");
            writer.write("              if (sInstance == null) \n");
            writer.write("                    sInstance = new PushRouterManager();\n");
            writer.write("             }\n");
            writer.write("         } \n");
            writer.write("        return sInstance; \n");
            writer.write("  } \n");

            writer.write("  public BasicMessage getGuide(String messageType, Map<String, String> map) { \n");
            writer.write("    return baseRouter(messageType, map); \n");
            writer.write("  } \n");
            writer.write("   \n");
            writer.write("   \n");
            writer.write("  public BasicMessage baseRouter(String messageType, Map<String, String> map) { \n");
            writer.write("    String classNamePath = allClassName.get(messageType); \n");
            writer.write("    if (classNamePath == null) { \n");
            writer.write("               new NullPointerException(\"messageType = \" + messageType + \" not find  map\"); \n");
            writer.write("        } \n");
            writer.write("           BasicMessage basicMessage = null; \n");
            writer.write("   try { \n");
            writer.write("         Class classObj = Class.forName(classNamePath); \n");
            writer.write("         Constructor constructor = classObj.getDeclaredConstructor(Map.class); \n");
            writer.write("         constructor.setAccessible(true);\n");
            writer.write("         basicMessage = (BasicMessage) constructor.newInstance(map); \n");
            writer.write("   } catch (IllegalAccessException e) { \n");
            writer.write("   } catch (InstantiationException e) { \n");
            writer.write("   } catch (ClassNotFoundException e) { \n");
            writer.write("   } catch (NoSuchMethodException e) { \n");
            writer.write("   } catch (InvocationTargetException e) { \n");
            writer.write("  } \n");
            writer.write("    return basicMessage; \n");
            writer.write("  } \n");
            writer.write("  } \n");
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }


}



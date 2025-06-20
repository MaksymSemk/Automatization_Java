package org.example;


import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("org.example.GenerateRefineryHelper")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class GenerateTreeHelperProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateRefineryHelper.class)) {
            String originalClassName = element.getSimpleName().toString();
            String helperClassName = originalClassName + "Helper";
            try {
                PackageElement pkg = processingEnv.getElementUtils().getPackageOf(element);
                String packageName = pkg.isUnnamed() ? "" : pkg.getQualifiedName().toString();
                JavaFileObject file = processingEnv.getFiler().createSourceFile(packageName + "." + helperClassName);
                try (PrintWriter out = new PrintWriter(file.openWriter())) {
                    if (!packageName.isEmpty()) {
                        out.println("package " + packageName + ";");
                    }
                    out.println("public class " + helperClassName + " {");
                    out.println("    public static void refine(" + element.getSimpleName() + " t) {");
                    out.println("        System.out.println(\"Refining tree: \" + t.getName());");
                    out.println("    }");
                    out.println("}");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}

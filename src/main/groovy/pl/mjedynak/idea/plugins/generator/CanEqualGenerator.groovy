package pl.mjedynak.idea.plugins.generator

import com.intellij.pom.java.LanguageLevel
import org.jetbrains.annotations.NotNull
import com.intellij.psi.*

class CanEqualGenerator {

    PsiMethod equalsMethod(@NotNull List<PsiField> equalsPsiFields, PsiClass psiClass, String equalsMethodName) {
        if (!equalsPsiFields.isEmpty()) {
            PsiElementFactory factory = getFactory(equalsPsiFields[0])
            StringBuilder methodText = new StringBuilder()
            methodText << "/**\n" +
                    "* See: http://www.artima.com/lejava/articles/equality.html\n" +
                    "* \n" +
                    "* @param other to compare to.\n" +
                    "* @return boolean if other is an instance of this.\n" +
                    "*/\n" +
                    "public final boolean canEqual(final Object other) {"
            methodText << "return (other instanceof ${psiClass.name});"
            methodText << '}'
            factory.createMethodFromText(methodText.toString(), null, LanguageLevel.JDK_1_6)
        }
    }

    private PsiElementFactory getFactory(PsiField psiField) {
        JavaPsiFacade.getInstance(psiField.project).elementFactory
    }
}



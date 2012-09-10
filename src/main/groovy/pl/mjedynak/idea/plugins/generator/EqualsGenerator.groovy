package pl.mjedynak.idea.plugins.generator

import com.intellij.pom.java.LanguageLevel
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElementFactory
import com.intellij.psi.PsiField
import com.intellij.psi.PsiMethod
import org.jetbrains.annotations.NotNull

class EqualsGenerator {

    PsiMethod equalsMethod(@NotNull List<PsiField> equalsPsiFields, PsiClass psiClass, String equalsMethodName) {
        if (!equalsPsiFields.isEmpty()) {
            PsiElementFactory factory = getFactory(equalsPsiFields[0])
            StringBuilder methodText = new StringBuilder()
            methodText << '@Override public final boolean equals(Object other) {'
            methodText << 'boolean result = false;'
            methodText << "if (other instanceof ${psiClass.name}) {"
            methodText << "final ${psiClass.name} that = (${psiClass.name}) other;"
            methodText << 'final EqualsBuilder eb = new EqualsBuilder()\n'
            equalsPsiFields.eachWithIndex { field, index ->
                methodText << ".append(${field.name}, that.${field.name})\n"
            }
            methodText << ';'
            methodText << 'result = eb.isEquals() && that.canEqual(this);'
            methodText << '}'
            methodText << 'return result;'
            methodText << '}'
            factory.createMethodFromText(methodText.toString(), null, LanguageLevel.JDK_1_6)
        }
    }

    private PsiElementFactory getFactory(PsiField psiField) {
        JavaPsiFacade.getInstance(psiField.project).elementFactory
    }
}



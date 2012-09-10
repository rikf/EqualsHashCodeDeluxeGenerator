package pl.mjedynak.idea.plugins.generator

import com.intellij.pom.java.LanguageLevel
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiElementFactory
import com.intellij.psi.PsiField
import com.intellij.psi.PsiMethod
import org.jetbrains.annotations.NotNull

class HashCodeGenerator {

    PsiMethod hashCodeMethod(@NotNull List<PsiField> hashCodePsiFields, String hashCodeMethodName) {
        if (!hashCodePsiFields.isEmpty()) {
            PsiElementFactory factory = getFactory(hashCodePsiFields[0])
            StringBuilder methodText = new StringBuilder()

            methodText << '@Override public final int hashCode() {'
            methodText << 'final HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(1,31)\n'
            hashCodePsiFields.eachWithIndex { field, index ->
                methodText << ".append(${field.name})\n"
            }
            methodText << ';'
            methodText << 'return hashCodeBuilder.toHashCode();'
            methodText << '}'

            factory.createMethodFromText(methodText.toString(), null, LanguageLevel.JDK_1_6)
        }
    }

    private PsiElementFactory getFactory(PsiField psiField) {
        JavaPsiFacade.getInstance(psiField.project).elementFactory
    }
}

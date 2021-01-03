package ma.basenautique.app;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ma.basenautique.app");

        noClasses()
            .that()
            .resideInAnyPackage("ma.basenautique.app.service..")
            .or()
            .resideInAnyPackage("ma.basenautique.app.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..ma.basenautique.app.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}

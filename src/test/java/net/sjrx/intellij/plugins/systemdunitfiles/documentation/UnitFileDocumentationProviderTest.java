package net.sjrx.intellij.plugins.systemdunitfiles.documentation;

import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import net.sjrx.intellij.plugins.systemdunitfiles.AbstractUnitFileTest;

import java.util.Collections;
import java.util.List;

public class UnitFileDocumentationProviderTest extends AbstractUnitFileTest {


  private final DocumentationProvider sut = new UnitFileDocumentationProvider();

  public void testGetUrlForUnknownKeyReturnsAnEmptyListOfUrls() {
    // Fixture Setup
    String file = "[Unit]\n"
                  + "BadKey=Hello Good Sir";

    PsiFile psiFile = setupFileInEditor("file.service", file);


    // Exercise SUT
    PsiElement badKey = getAllKeysInFile(psiFile).get(0);

    List<String> urls = sut.getUrlFor(badKey, badKey);

    // Verification
    assertEmpty(urls);
  }

  public void testGetUrlForUnknownKeySeparatorReturnsAnEmptyListOfUrls() {
    // Fixture Setup
    String file = "[Unit]\n"
                  + "BadKey=Hello Good Sir";

    PsiFile psiFile = setupFileInEditor("file.service", file);


    // Exercise SUT
    PsiElement badKeySeparator = getAllSeparatorsInFile(psiFile).get(0);

    List<String> urls = sut.getUrlFor(badKeySeparator, badKeySeparator);

    // Verification
    assertEmpty(urls);
  }

  public void testGetUrlForKnownKeyReturnsAValidUrl() {
    // Fixture Setup
    String file = "[Unit]\n"
                  + "Documentation=http://www.google.com";

    PsiFile psiFile = setupFileInEditor("file.service", file);

    // Exercise SUT
    PsiElement documentationKey = getAllKeysInFile(psiFile).get(0);

    List<String> urls = sut.getUrlFor(documentationKey, documentationKey);

    // Verification
    assertEquals(Collections.singletonList("https://www.freedesktop.org/software/systemd/man/systemd.unit.html#Documentation="), urls);
  }

  public void testGetUrlForKnownKeySeparatorReturnsAValidUrl() {
    // Fixture Setup
    String file = "[Unit]\n"
                  + "Documentation=http://www.google.com";

    PsiFile psiFile = setupFileInEditor("file.service", file);

    // Exercise SUT
    PsiElement documentationKeySeparator = getAllSeparatorsInFile(psiFile).get(0);

    List<String> urls = sut.getUrlFor(documentationKeySeparator, documentationKeySeparator);

    // Verification
    assertEquals(Collections.singletonList("https://www.freedesktop.org/software/systemd/man/systemd.unit.html#Documentation="), urls);
  }

  public void testGetUrlForUnknownSectionReturnsAnEmptyListOfUrls() {
    // Fixture Setup
    String file = "[X-Unit]\n"
                  + "Documentation=http://www.google.com";

    PsiFile psiFile = setupFileInEditor("file.service", file);

    // Exercise SUT
    PsiElement unknownSectionHeader = getAllSectionInFile(psiFile).get(0);

    List<String> urls = sut.getUrlFor(unknownSectionHeader, unknownSectionHeader);

    // Verification
    assertEmpty(urls);
  }

  public void testGetUrlForKnownSectionReturnsAValidUrl() {
    // Fixture Setup
    String file = "[Unit]\n"
                  + "Documentation=http://www.google.com";

    PsiFile psiFile = setupFileInEditor("file.service", file);

    // Exercise SUT
    PsiElement unknownSectionHeader = getAllSectionInFile(psiFile).get(0);

    List<String> urls = sut.getUrlFor(unknownSectionHeader, unknownSectionHeader);

    // Verification
    assertEquals(Collections.singletonList("https://www.freedesktop.org/software/systemd/man/systemd.unit.html#%5BUnit%5D%20Section%20Options"), urls);
  }


  public void testGenerateDocForUnknownKeyReturnsNull() {
    // Fixture Setup
    String file = "[Unit]\n"
                  + "BadKey=Hello Good Sir";

    PsiFile psiFile = setupFileInEditor("file.service", file);


    // Exercise SUT
    PsiElement badKey = getAllKeysInFile(psiFile).get(0);

    String doc = sut.generateDoc(badKey, badKey);

    // Verification
    assertNull(doc);
  }

  public void testGenerateDocForUnknownKeySeparatorReturnsNull() {
    // Fixture Setup
    String file = "[Unit]\n"
                  + "BadKey=Hello Good Sir";

    PsiFile psiFile = setupFileInEditor("file.service", file);


    // Exercise SUT
    PsiElement badKeySeparator = getAllSeparatorsInFile(psiFile).get(0);

    String doc = sut.generateDoc(badKeySeparator, badKeySeparator);

    // Verification
    assertNull(doc);
  }

  public void testGenerateDocForKnownKeyReturnsSomeValidText() {
    // Fixture Setup
    String file = "[Unit]\n"
                  + "Documentation=http://www.google.com";

    PsiFile psiFile = setupFileInEditor("file.service", file);

    // Exercise SUT
    PsiElement documentationKey = getAllKeysInFile(psiFile).get(0);

    String doc = sut.generateDoc(documentationKey, documentationKey);

    // Verification
    assertNotNull(doc);
    assertTrue((doc.length() > 0));
  }

  public void testGenerateDocForKnownKeySeparatorReturnsSomeValidText() {
    // Fixture Setup
    String file = "[Unit]\n"
                  + "Documentation=http://www.google.com";

    PsiFile psiFile = setupFileInEditor("file.service", file);

    // Exercise SUT
    PsiElement documentationKeySeparator = getAllSeparatorsInFile(psiFile).get(0);

    String doc = sut.generateDoc(documentationKeySeparator, documentationKeySeparator);

    // Verification
    assertNotNull(doc);
    assertTrue((doc.length() > 0));
  }

  public void testGenerateDocForUnknownSectionReturnsNull() {
    // Fixture Setup
    String file = "[X-Unit]\n"
                  + "Documentation=http://www.google.com";

    PsiFile psiFile = setupFileInEditor("file.service", file);

    // Exercise SUT
    PsiElement unknownSectionHeader = getAllSectionInFile(psiFile).get(0);

    String doc = sut.generateDoc(unknownSectionHeader, unknownSectionHeader);

    // Verification
    assertNull(doc);
  }

  public void testGenerateDocForKnownSectionReturnsSomeValidText() {
    // Fixture Setup
    String file = "[Unit]\n"
                  + "Documentation=http://www.google.com";

    PsiFile psiFile = setupFileInEditor("file.service", file);

    // Exercise SUT
    PsiElement unknownSectionHeader = getAllSectionInFile(psiFile).get(0);

    String doc = sut.generateDoc(unknownSectionHeader, unknownSectionHeader);

    // Verification
    assertNotNull(doc);

    assertTrue(doc.length() > 1);

  }
}
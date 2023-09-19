package edu.stanford.bmir.radx.datadictionary.lib;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-19
 */
public class EnumerationParserTests {

    private EnumerationParser parser;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void shouldParseChoice() throws ParseException {
        var parser = new EnumerationParser(new StringReader("""
                                        "Hello"=[World]
                                        """));
        var choice = parser.Choice();
        Assertions.assertThat(choice.label()).isEqualTo("World");
        Assertions.assertThat(choice.value()).isEqualTo("Hello");
        Assertions.assertThat(choice.iri()).isEmpty();
    }
    @Test
    public void shouldParseChoiceWithIri() throws ParseException {
        var parser = new EnumerationParser(new StringReader("""
                                        "Hello"=[World](https://example.org)
                                        """));
        var choice = parser.Choice();
        Assertions.assertThat(choice.label()).isEqualTo("World");
        Assertions.assertThat(choice.value()).isEqualTo("Hello");
        Assertions.assertThat(choice.iri()).isEqualTo("https://example.org");
    }

    @Test
    public void shouldParseChoiceWithWhiteSpace() throws ParseException {
        var parser = new EnumerationParser(new StringReader("""
                                        "Hello"  =  [World]
                                        """));
        var choice = parser.Choice();
        Assertions.assertThat(choice.label()).isEqualTo("World");
        Assertions.assertThat(choice.value()).isEqualTo("Hello");
    }

    @Test
    public void shouldParseChoiceWithWhiteSpacePreserved() throws ParseException {
        var parser = new EnumerationParser(new StringReader("""
                                        " Hello "  =  [ World ]
                                        """));
        var choice = parser.Choice();
        Assertions.assertThat(choice.label()).isEqualTo(" World ");
        Assertions.assertThat(choice.value()).isEqualTo(" Hello ");
    }

    @Test
    public void shouldParseChoiceWithEscapedDoubleQuote() throws ParseException {
        var parser = new EnumerationParser(new StringReader("""
                                                                    "Value \\"X\\""=[The Label]
                                                                    """));
        var choice = parser.Choice();
        Assertions.assertThat(choice.value()).isEqualTo("Value \"X\"");
    }

    @Test
    public void shouldParseChoiceWithEscapedSquareBracket() throws ParseException {
        var parser = new EnumerationParser(new StringReader("""
                                                                    "Value"=[The \\] Label]
                                                                    """));
        var choice = parser.Choice();
        Assertions.assertThat(choice.label()).isEqualTo("The ] Label");
    }

    @Test
    public void shouldParseEnumerationWithNonAsciiCharacters() throws ParseException {
        var parser = new EnumerationParser(new StringReader("""
                                                                    "98"=[Don’t know] |
                                                                    """));
        var choice = parser.Choice();
        Assertions.assertThat(choice.label()).isEqualTo("Don’t know");
        Assertions.assertThat(choice.value()).isEqualTo("98");
    }
}

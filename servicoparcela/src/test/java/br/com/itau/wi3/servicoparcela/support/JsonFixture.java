package br.com.itau.wi3.servicoparcela.support;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Carrega massas de teste (fixtures) em JSON a partir de src/test/resources.
 */
public final class JsonFixture {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonFixture() {
    }

    public static String asString(final String classpathResource) {
        try (InputStream inputStream = inputStream(classpathResource)) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (final IOException e) {
            throw new UncheckedIOException("Erro ao ler fixture " + classpathResource, e);
        }
    }

    public static <T> T as(final String classpathResource, final Class<T> type) {
        try (InputStream inputStream = inputStream(classpathResource)) {
            return OBJECT_MAPPER.readValue(inputStream, type);
        } catch (final IOException e) {
            throw new UncheckedIOException("Erro ao desserializar fixture " + classpathResource, e);
        }
    }

    public static <T> T as(final String classpathResource, final TypeReference<T> type) {
        try (InputStream inputStream = inputStream(classpathResource)) {
            return OBJECT_MAPPER.readValue(inputStream, type);
        } catch (final IOException e) {
            throw new UncheckedIOException("Erro ao desserializar fixture " + classpathResource, e);
        }
    }

    private static InputStream inputStream(final String classpathResource) {
        return Objects.requireNonNull(
                JsonFixture.class.getResourceAsStream(classpathResource),
                "Fixture não encontrada no classpath: " + classpathResource
        );
    }
}

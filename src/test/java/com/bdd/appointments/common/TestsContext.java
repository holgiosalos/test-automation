package com.bdd.appointments.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Holmes Salazar
 */
public class TestsContext
{
    private final Properties properties = new Properties();

    public TestsContext()
    {
        loadProperites("test.properties");
    }

    private void loadProperites(final String propertiesFileName)
    {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName))
        {
            properties.load(inputStream);
        }
        catch (IOException exception)
        {
            throw new IllegalStateException("Error loading " + propertiesFileName + " file", exception);
        }
    }

    public String getBaseUrl()
    {
        return properties.getProperty("baseUrl");
    }

    public String getConfiguredDriver()
    {
        return properties.getProperty("driver");
    }
}

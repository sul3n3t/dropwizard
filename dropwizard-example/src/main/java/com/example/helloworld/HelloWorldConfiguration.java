package com.example.helloworld;

import com.example.helloworld.core.Template;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.tls.TlsConfiguration;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@SuppressWarnings("FieldMayBeFinal")
public class HelloWorldConfiguration extends Configuration {

    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @NotNull
    @JsonProperty
    private TlsConfiguration tls = new TlsConfiguration();

    @Valid
    @NotNull
    @JsonProperty
    private DatabaseConfiguration database = new DatabaseConfiguration();

    public String getTemplate() {
        return template;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public TlsConfiguration getTls() {
        return tls;
    }

    public Template buildTemplate() {
        return new Template(template, defaultName);
    }

    public DatabaseConfiguration getDatabaseConfiguration() {
        return database;
    }
}

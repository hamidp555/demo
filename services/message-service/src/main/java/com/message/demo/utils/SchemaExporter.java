package com.message.demo.utils;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.hibernate.tool.schema.spi.ScriptTargetOutput;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class SchemaExporter {

    private static Logger LOG = LoggerFactory.getLogger(SchemaExporter.class);
    private String dialect;
    private String entityPackage;
    private MetadataSources metaData;
    private SchemaExport schemaExport;
    private final String outputDir;

    public SchemaExporter(String dialect, String entityPackage, String outputDir) throws SQLException {
        this.dialect = dialect;
        this.entityPackage = entityPackage;
        this.outputDir = outputDir;
        metaData = createMetaData();
        schemaExport = new SchemaExport();
        schemaExport.setHaltOnError(true);
        schemaExport.setFormat(true);
        schemaExport.setDelimiter(";");
    }

    public void export() {
        exportCreate(outputDir + "/create.schema.sql");
        exportDrop(outputDir + "/drop.schema.sql");
    }

    private void exportCreate(String destination) {
        ScriptTargetOutput targetOutput = new ScriptTargetOutputToFile(
                new File(destination),
                StandardCharsets.ISO_8859_1.name());
        schemaExport.perform(SchemaExport.Action.CREATE, metaData.buildMetadata(), targetOutput);
    }

    private void exportDrop(String destination) {
        ScriptTargetOutput targetOutput = new ScriptTargetOutputToFile(
                new File(destination),
                StandardCharsets.ISO_8859_1.name());
        schemaExport.perform(SchemaExport.Action.DROP, metaData.buildMetadata(), targetOutput);
    }

    private MetadataSources createMetaData() throws SQLException {
        MetadataSources metadata = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .applySetting("hibernate.dialect", dialect)
                        .build());

        final Reflections reflections = new Reflections(entityPackage);
        for (Class<?> cl : reflections.getTypesAnnotatedWith(MappedSuperclass.class)) {
            metadata.addAnnotatedClass(cl);
            LOG.info("Mapped = " + cl.getName());
        }
        for (Class<?> cl : reflections.getTypesAnnotatedWith(Entity.class)) {
            metadata.addAnnotatedClass(cl);
            LOG.info("Mapped = " + cl.getName());
        }
        return metadata;
    }

    // This class could possibly be moved to a seperate project for gradle plugin
    public static void main(String[] args) throws IOException, SQLException {
        SchemaExporter exporter = new SchemaExporter(
                "org.hibernate.dialect.PostgreSQLDialect",
                "com.message.demo.model",
                "services/message-service/src/main/resources");
        exporter.export();
    }

}

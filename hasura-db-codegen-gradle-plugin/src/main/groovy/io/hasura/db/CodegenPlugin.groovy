package io.hasura.db

import org.gradle.api.Project
import org.gradle.api.Plugin

import io.hasura.db.util.Configuration
import io.hasura.db.util.GenerationUtil

class CodegenPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('hasura-db-codegen') << {
            // Read admin credentials
            Properties props = new Properties()
            props.load(new FileInputStream("hasura.properties"))

            // Build Generation Configuration
            // ------------------------------
            def cfg = new io.hasura.db.util.Configuration();
            cfg.setDir(props.getProperty("dir"))
            cfg.setPackageName(props.getProperty("package"))
            cfg.setDBUrl(props.getProperty("url"))
            cfg.setDBPrefix(props.getProperty("dbprefix"))
            cfg.setAdminAPIKey(props.getProperty("adminAPIKey"))

            // Run the code generator
            // ----------------------
            io.hasura.db.util.GenerationUtil.generate(cfg)
        }
    }
}

package ru.darvell.job.bgutils.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Templater {
    private static final String HTML_DIR = "src/main/resources/templates/";
    private static final Configuration CFG = new Configuration(Configuration.VERSION_2_3_23);

    public static String getPage(String filename, Map<String, Object> data) throws IOException{
        Writer stream = new StringWriter();
        CFG.setDirectoryForTemplateLoading(new File(HTML_DIR));
        try {
            Template template = CFG.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

    public static String getPage(String filename) {

        Writer stream = new StringWriter();

        try {
            Template template = CFG.getTemplate(File.separator + filename);
            Map<String, String> data = new HashMap<>();
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

}
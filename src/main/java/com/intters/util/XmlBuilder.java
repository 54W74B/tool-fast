package com.intters.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

/**
 * XML文件操作
 *
 * @author Ruison
 * @date 2018/7/16.
 */
public class XmlBuilder {

    /**
     * xml 转 String
     *
     * @param path xml所在路径，可直接读取resources路线下的xml文件，例如：Provinces.xml
     * @return xml的String类型
     * @throws Exception
     */
    private String getBuffer(String path) throws Exception {
        Resource resource = new ClassPathResource(path);
        BufferedReader bf = new BufferedReader(new InputStreamReader(resource.getInputStream(), "utf-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = bf.readLine()) != null) {
            buffer.append(line);
        }
        bf.close();
        return buffer.toString();
    }

    /**
     * XML 转POJO
     * @param clazz 转化的POJO类
     * @param xmlStr xml的String类型
     * @return 返货POJO对象
     * @throws Exception
     */
    public static Object xmlStrToObject(Class<?> clazz, String xmlStr) throws Exception {
        Object xmlObject = null;
        Reader reader = null;
        JAXBContext context = JAXBContext.newInstance(clazz);

        //XML 转对象的接口
        Unmarshaller unmarshaller = context.createUnmarshaller();

        reader = new StringReader(xmlStr);
        xmlObject = unmarshaller.unmarshal(reader);

        if (reader != null) {
            reader.close();
        }

        return xmlObject;
    }
}

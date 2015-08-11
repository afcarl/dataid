package org.aksw.dataid.datahub.propertymapping;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

public class StaticHelper 
{
	public static ObjectMapper mapper = new ObjectMapper();
		
	@SuppressWarnings("unchecked")
	public static <E> E dynamicGet(Object object, String fieldName) 
	{
	    Class<?> clazz = object.getClass();
	    while (clazz != null) {
	        try {
	            Field field = clazz.getDeclaredField(fieldName);
	            field.setAccessible(true);
	            return (E) field.get(object);
	        } catch (NoSuchFieldException e) {
	            clazz = clazz.getSuperclass();
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    return null;
	}

	public static void writeToFile(String content, String filePath) {
		try {
 
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String readFile(String filePath) {
		 
		File file = new File(filePath);
		StringBuilder sb = new StringBuilder();
		try (FileInputStream fis = new FileInputStream(file)) { 

			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line;
			while ((line = br.readLine()) != null) 
			{
				sb.append(line + "\n");
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String readUrl(String url)
	{
		StringBuilder sb = new StringBuilder();
		InputStream in = null;
		try {
			URL u = new URL(url);
			in = u.openStream();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (in != null) 
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			try {
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sb.toString();
		}
		return null;
	}
	
    public static <T> T castJsonToObject(String jsonString, Class<T> clas)
    {
    	return castJsonToObject(jsonString, clas, "");
    }
    public static <T> T castJsonToObject(String json, Class<T> clas, String subNode)
    {
		try {
			JsonNode node = mapper.readValue(json, JsonNode.class);
			JavaType mapping = mapper.getTypeFactory().constructType(clas);
			JsonNode graph = node.get(subNode);
            T config = mapper.readValue(graph.toString(), mapping);
			return config;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return null;
    }
}
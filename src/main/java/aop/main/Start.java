package aop.main;

import aop.objects.FileManager;
import aop.objects.FileManager2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        FileManager fileManager = (FileManager) context.getBean("fileManager");
        fileManager.getExtensionList("/Users/admin/Documents/Книги");
        fileManager.getExtensionCount("/Users/admin/Documents/Книги");

        FileManager2 fileManager2 = (FileManager2) context.getBean("fileManager2");
        fileManager2.getExtensionList("/Users/admin/Documents/Книги");
        fileManager2.getExtensionCount("/Users/admin/Documents/Книги");
    }
}

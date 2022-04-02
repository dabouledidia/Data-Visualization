package file.manager;

public class StructuredFileManagerFactory {

    public StructuredFileManagerFactory(){}

    public StructuredFileManagerInterface createStructuredFileManager(){
        return new StructuredFileManager();
    }
}

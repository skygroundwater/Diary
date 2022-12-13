package diary.typeOfTask;
public enum TypesOfTask {
    PERSONAL("<личная>"), WORKING("<рабочая>");
    private String type;
    TypesOfTask(String type){
        setType(type);
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    @Override
    public String toString() {
        return  type;
    }
}
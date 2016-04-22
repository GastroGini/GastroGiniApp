package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components;


public class TestSelectable<ET> {

    private boolean selected;
    ET item;

    public TestSelectable(ET wrapped){
        item = wrapped;
        selected = false;
    }

    public ET getItem(){
        return item;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void toggleSelected(){
        this.selected = !selected;
    }
}

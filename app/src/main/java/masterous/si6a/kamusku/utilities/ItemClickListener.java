package masterous.si6a.kamusku.utilities;

public interface ItemClickListener<T> {
    void onItemClick(T data, int position);
    void onItemLongClick(T data, int position);
}
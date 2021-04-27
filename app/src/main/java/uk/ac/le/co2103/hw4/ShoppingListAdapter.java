package uk.ac.le.co2103.hw4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>{

    private ArrayList<ShoppingList> shoppingLists;
    private OnShoppingListClickListener shoppingListClickListener;
    private OnShoppingListLongPressListener shoppingListLongPressListener;

    public interface OnShoppingListClickListener{
        void onShoppingListClick(int position);
    }

    public interface OnShoppingListLongPressListener{
        void onShoppingListLongPress(int position);
    }

    public void setShoppingListClickListener(OnShoppingListClickListener clickListener){
        this.shoppingListClickListener = clickListener;
    }

    public void setShoppingListLongPressListener(OnShoppingListLongPressListener longPressListener){
        this.shoppingListLongPressListener = longPressListener;
    }

    public ShoppingListAdapter(ArrayList<ShoppingList> shoppingLists){
        if(shoppingLists == null)this.shoppingLists = new ArrayList<>();
        else this.shoppingLists = shoppingLists;
    }

    public void setShoppingLists(ArrayList<ShoppingList> shoppingLists){
        this.shoppingLists = shoppingLists;
    }

    public static class ShoppingListViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtListName;
        private final ImageView imgList;

        public ShoppingListViewHolder(final View view, final OnShoppingListClickListener clickListener,
                                      OnShoppingListLongPressListener longPressListener){
            super(view);
            this.txtListName = view.findViewById(R.id.txt_list_name);
            this.imgList = view.findViewById(R.id.img_list);

            view.setOnClickListener(view1 -> {
                if(clickListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION)clickListener.onShoppingListClick(position);
                }
            });

            view.setOnLongClickListener(view1 -> {
                if(longPressListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION)longPressListener
                            .onShoppingListLongPress(position);
                    return true;
                }
                return false;
            });
        }
    }

    @Override
    public ShoppingListAdapter.ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View shoppingListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ShoppingListViewHolder(shoppingListView, shoppingListClickListener, shoppingListLongPressListener);
    }

    @Override
    public void onBindViewHolder(ShoppingListAdapter.ShoppingListViewHolder holder, int position){
        String listName = shoppingLists.get(position).getName();
        byte[] imageBytes = shoppingLists.get(position).getImage();

        if(imageBytes != null){
            Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imgList.setImageBitmap(image);
        }

        holder.txtListName.setText(listName);
    }

    @Override
    public int getItemCount(){
        return this.shoppingLists.size();
    }

}

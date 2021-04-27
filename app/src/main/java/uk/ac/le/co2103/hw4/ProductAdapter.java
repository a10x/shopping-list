package uk.ac.le.co2103.hw4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    private onProductClickListener productClickListener;

    public interface onProductClickListener{
        void onProductClick(int position);
    }

    public ProductAdapter(List<Product> products){
        this.products = products;
    }

    public void setProducts(List<Product> products){this.products = products;}

    public void setProductClickListener(onProductClickListener productClickListener){
        this.productClickListener = productClickListener;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtProduct;
        private final TextView txtProductQU;

        public ProductViewHolder(final View view, final onProductClickListener clickListener){
            super(view);
            this.txtProduct = view.findViewById(R.id.txt_product);
            this.txtProductQU = view.findViewById(R.id.txt_quan_unit);

            view.setOnClickListener(view1 ->{
                if(clickListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) clickListener.onProductClick(position);
                }
            });
        }
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_product, parent, false);
        return new ProductViewHolder(productListView, productClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product p = products.get(position);
        String productName = p.getName();
        String quanUnit = p.getQuantity() + " " +  p.getUnit().name();

        holder.txtProduct.setText(productName);
        holder.txtProductQU.setText(quanUnit);
    }

    @Override
    public int getItemCount() {
        if(this.products == null) return 0;
        return this.products.size();
    }
}

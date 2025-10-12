package com.example.lab2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private String[] itemTitles = {
        "White",
            "Whole Wheat",
            "Multi Grain",
            "Rye",
            "Sourdough",
            "Pumpernickel",
            "Baguetet",
            "Boule",
            "Ciabatta",
            "Challah",
            "Broiche",
            "Flatbread",
            "English muffin",
            "Bagel",
            "Bialy"
    };
    private String[] itemDesc = {
            "White bread comes from white flour, which is the result of milling once the bran and germ are removed from the wheatberry.",
            "Whole wheat bread is made from flour that uses the whole wheatberry.  It has more nutritional value.",
            "Multigrain bread is made from at least two different grains—think oat, barley, millet, flax, etc.",
            "Rye bread is made with rye berry.  Rye bread can vary in colour, texture, and flavour depending on the amount of rye flour used.",
            "Sourdough is made from entirely wild yeast, which feeds and grows on a water-flour combination known as a \"starter.\"",
            "True pumpernickel bread is a German variety of rye bread made using only rye flour and a long baking time set at a low temperature.",
            "These long, crusty loaves can range in length to upwards of two-and-a-half feet and are beloved for their crisp exteriors and soft, chewy interiors.",
            "Boules are made with any blend of flour and even mix-ins like olives or fresh herbs.\n",
            "Made from a dough that includes olive oil, ciabatta is known for its porous interior featuring alveolar-like air pockets",
            "A symbolic bread within the Jewish faith, challah is often braided into loaves and made from an egg-heavy dough, which gives the bread its signature yellow hue.\n",
            "It's a rich dough made using both eggs and butter, which lends itself well to dishes like French toast and bread puddings.\n",
            "Flatbread is more of an overarching type of bread that applies to multiple international options including naan, pita, tortillas, lavash, focaccia, roti and matzoh.\n",
            "The dough is cooked over a heat source on both sides, which gives them their signature browned exteriors.",
            "They have a hole in the center and the dough is boiled before being baked, thus giving the bagel it's signature glossy exterior.",
            "Bialys have a depression that is filled with many of the same toppings that appear on bagels, and is baked straight away from dough.",
    };
    private int[] imageRes = {
            R.drawable.bread1,
            R.drawable.bread2,
            R.drawable.bread3,
            R.drawable.bread4,
            R.drawable.bread5,
            R.drawable.bread6,
            R.drawable.bread7,
            R.drawable.bread8,
            R.drawable.bread9,
            R.drawable.bread10,
            R.drawable.bread11,
            R.drawable.bread12,
            R.drawable.bread13,
            R.drawable.bread14,
            R.drawable.bread15,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a list of 15 items
        List<ItemAdapter.Item> items = new ArrayList<>();
        for (int i = 1; i < 15; i++) {
            items.add(new ItemAdapter.Item(itemTitles[i],itemDesc[i],imageRes[i]));
        }
        itemAdapter = new ItemAdapter(items,this);
        recyclerView.setAdapter(itemAdapter);
    }
}
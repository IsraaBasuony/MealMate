package com.iti.mealmate.model;

import com.iti.mealmate.R;

import java.util.ArrayList;

public class FlagSource {

        public ArrayList<ImageModel> getFlags() {
            ArrayList<ImageModel> flags = new ArrayList<>();
            flags.add(new ImageModel(R.drawable.american, "American"));
            flags.add(new ImageModel(R.drawable.british, "British"));
            flags.add(new ImageModel(R.drawable.canidian, "Canadian"));
            flags.add(new ImageModel(R.drawable.french, "French"));
            flags.add(new ImageModel(R.drawable.chinese, "Chinese"));
            flags.add(new ImageModel(R.drawable.croatian, "Croatian"));
            flags.add(new ImageModel(R.drawable.dutch, "Dutch"));
            flags.add(new ImageModel(R.drawable.egyptian, "Egyptian"));
            flags.add(new ImageModel(R.drawable.filipino, "Filipino"));
            flags.add(new ImageModel(R.drawable.greek, "Greek"));
            flags.add(new ImageModel(R.drawable.indian, "Indian"));
            flags.add(new ImageModel(R.drawable.irish, "Irish"));
            flags.add(new ImageModel(R.drawable.italian, "italian"));
            flags.add(new ImageModel(R.drawable.jamaican, "Jamaican"));
            flags.add(new ImageModel(R.drawable.japan, "Japanese"));
            flags.add(new ImageModel(R.drawable.kenyan, "Kenyan"));
            flags.add(new ImageModel(R.drawable.malaysian, "Malaysian"));
            flags.add(new ImageModel(R.drawable.mexican, "Mexican"));
            flags.add(new ImageModel(R.drawable.moroccan, "Moroccan"));
            flags.add(new ImageModel(R.drawable.polish, "Polish"));
            flags.add(new ImageModel(R.drawable.portuguese, "Portuguese"));
            flags.add(new ImageModel(R.drawable.russian, "Russian"));
            flags.add(new ImageModel(R.drawable.spanish, "Spanish"));
            flags.add(new ImageModel(R.drawable.thai, "Thai"));
            flags.add(new ImageModel(R.drawable.tunisian, "Tunisian"));
            flags.add(new ImageModel(R.drawable.turkish, "Turkish"));
            flags.add(new ImageModel(R.drawable.vietnamese, "Vietnamese"));
            return flags;
        }
        public int getFlagIdByName(String name) {
            ArrayList<ImageModel> flags = getFlags();

            for (ImageModel imageModel : flags) {
                if (imageModel.getName().equalsIgnoreCase(name)) {
                    return imageModel.getFlagId();
                }
            }

            return R.drawable.egyptian;
        }
}
 class ImageModel {
    private int flagId;
    private String name;

    public ImageModel(int flagId, String name) {
        this.flagId = flagId;
        this.name = name;
    }

    public int getFlagId() {
        return flagId;
    }

    public String getName() {
        return name;
    }
}

package com.kgtb32.myedt.model;


import android.view.View;

import com.kgtb32.myedt.R;

public class ThemeHelper {

    public static String[] generateThemeNameArray(View root){
        return root.getResources().getStringArray(R.array.themeArray);
    }

    public static int[] generateThemeIDArray(){
        int[] themes = {
                R.style.Rouge_Pomme,
                R.style.Rose_Bonbon,
                R.style.Fushia,
                R.style.Parme,
                R.style.Bleu,
                R.style.Bleu_Pale,
                R.style.Bleu_Ciel,
                R.style.Bleu_Turquoise,
                R.style.Vert_Turquoise,
                R.style.Vert_Cactus,
                R.style.Vert_Bouteille,
                R.style.Vert_Pomme,
                R.style.Jaune_Pulco,
                R.style.Jaune_Citron,
                R.style.Jaune_Agrummes,
                R.style.Orange_Abricot,
                R.style.Marron_Chataigne,
                R.style.Gris_de_Roche,
                R.style.Lueur_de_Bleu
        };
        return themes;
    }

}

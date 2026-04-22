package com.klikli_dev.codedefinedgui.datagen.lang;

import com.klikli_dev.codedefinedgui.CodeDefinedGui;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ENUSLanguageProvider extends LanguageProvider {
    public ENUSLanguageProvider(PackOutput output) {
        super(output, CodeDefinedGui.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addConfig();
    }

    protected void addConfig(){
        this.add(CodeDefinedGui.MODID + ".configuration.title", "Code Defined Gui Configs");
    }
}

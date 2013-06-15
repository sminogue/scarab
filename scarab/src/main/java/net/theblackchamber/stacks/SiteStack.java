package net.theblackchamber.stacks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;

public class SiteStack implements JavaScriptStack
{
    @Inject
    private AssetSource assetSource;

    @Override
    public List<String> getStacks()
    {
        return Collections.emptyList();
    }

    @Override
    public List<Asset> getJavaScriptLibraries()
    {
        List<Asset> assets = new ArrayList<Asset>();
        assets.add(assetSource.getUnlocalizedAsset("context:js/aaUtility.js"));
        assets.add(assetSource.getUnlocalizedAsset("context:js/layout.js"));
        assets.add(assetSource.getUnlocalizedAsset("context:lib/jquery-cookie/jquery.cookie.js"));
        
        return assets;
    }

    @Override
    public List<StylesheetLink> getStylesheets()
    {
        List<StylesheetLink> stylesheets = new ArrayList<StylesheetLink>();
        stylesheets.add(new StylesheetLink(assetSource.getUnlocalizedAsset("context:css/layout.css")));
        stylesheets.add(new StylesheetLink(assetSource.getUnlocalizedAsset("context:css/projects.css")));
        return stylesheets;
    }

    @Override
    public String getInitialization()
    {
        return null;
    }
}

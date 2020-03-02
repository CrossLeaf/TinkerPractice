package com.example.tinkerpractice;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.entry.DefaultApplicationLike;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareConstants;

@DefaultLifeCycle(
        application = "com.example.tinkerpractice.TinkerApp",             //application name to generate
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class TinkerAppLike extends DefaultApplicationLike {
    public TinkerAppLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                         long applicationStartElapsedTime, long applicationStartMillisTime,
                         Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        TinkerManager.setTinkerApplicationLike(this);

        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());
    }
}

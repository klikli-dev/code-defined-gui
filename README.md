<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Code-Defined GUI    

A Minecraft mod that allows other mods to define GUIs in code – without the need to create the full layout in an image editor.
This allows for faster creation and prototyping of GUIs, and enables dynamic GUI layout changes without needing multiple hand-made background images to swap between.

## Curseforge

https://www.curseforge.com/minecraft/mc-mods/code-defined-gui

## Maven

See https://cloudsmith.io/~klikli-dev/repos/mods/groups/ for available versions.

```gradle
repositories {

  ...

  maven {
    url "https://dl.cloudsmith.io/public/klikli-dev/mods/maven/"
    content {
        includeGroup "com.klikli_dev"
    }
  }
  
  ...
  
}
```

```gradle
dependencies {
 
    ...
    
    implementation "com.klikli_dev:codedefinedgui-${minecraft_version}-neoforge:${code_defined_gui_version}"
    
    ...
    
}
```

## Documentation

- [Docs index](./docs/README.md)

## Thanks

[![Hosted By: Cloudsmith](https://img.shields.io/badge/OSS%20hosting%20by-cloudsmith-blue?logo=cloudsmith&style=for-the-badge)](https://cloudsmith.com)

Package repository hosting is graciously provided by [Cloudsmith](https://cloudsmith.com).
Cloudsmith is the only fully hosted, cloud-native, universal package management solution, that
enables your organization to create, store and share packages in any format, to any place, with total
confidence.

## Licensing

Copyright 2026 klikli-dev

Code is licensed under the MIT license, view [LICENSES/MIT](./LICENSES/MIT.txt).   
Assets are licensed under the CC-BY-SA-4.0 license, view [LICENSES/CC-BY-SA-4.0](./LICENSES/CC-BY-4.0.txt).   

There are third party code and assets in this project which may be under different licenses and copyrights. We follow the [REUSE Standard for Software Licensing](https://reuse.software/), so you can look up the license terms for each file, or use the [REUSE tool](https://github.com/fsfe/reuse-tool) to generate an SPDX report.

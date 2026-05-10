<!--
SPDX-FileCopyrightText: 2026 klikli-dev

SPDX-License-Identifier: MIT
-->

# Code-Defined GUI    

Code Defined GUI (CDG) is a NeoForge library mod for building Minecraft GUIs from layout specs, style sheets, and reusable widgets.

The API surface has three areas:

- `com.klikli_dev.codedefinedgui.api.*` — generic layout, style, texture, and widget APIs
- `com.klikli_dev.codedefinedgui.premade.filter.*` — shipped list and attribute filter implementations built on that API
- `com.klikli_dev.codedefinedgui.internal.*` — framework internals, bootstrap, registries, and debug/reference plumbing
- `com.klikli_dev.codedefinedgui.example.*` — example and test code (not always super clean)

Only `api.*` and `premade.*` are supported import targets. Do not depend on `internal.*` from downstream mods.

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

Alternatively if CDG should be bundled in your mod's jar file:

```gradle
dependencies {
 
    ...
    
        jarJar(implementation(group: "com.klikli_dev", name: "codedefinedgui-${minecraft_version}-neoforge")) {
        version {
            prefer code_defined_gui_version
        }
    }
    
    ...
    
}
```

## Documentation

- [Docs index](./docs/README.md)
- [Architecture overview](./docs/architecture-overview.md)
- [API surface and stability policy](./docs/api-surface-and-stability-policy.md)
- [Using the library](./docs/using-the-library.md)
- [Layout-driven quick start](./docs/layout-driven-quick-start.md)
- [Premade filters](./docs/premade-filters.md)
- [Examples guide](./docs/examples-guide.md)
- [Styling premade GUIs](./docs/styling-premade-guis.md)

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

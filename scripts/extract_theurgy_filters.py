#!/usr/bin/env python3
# SPDX-FileCopyrightText: 2026 klikli-dev
#
# SPDX-License-Identifier: MIT

from pathlib import Path
import shutil
from PIL import Image


ROOT = Path(__file__).resolve().parents[1]
THEURGY = Path(r"J:/Projects/Minecraft/theurgy/src/main/resources/assets/theurgy/textures")
OUT = ROOT / "src/main/resources/assets/codedefinedgui/textures"


def crop(src: Path, dst: Path, box: tuple[int, int, int, int]) -> None:
    dst.parent.mkdir(parents=True, exist_ok=True)
    Image.open(src).crop(box).save(dst)


def copy(src: Path, dst: Path) -> None:
    dst.parent.mkdir(parents=True, exist_ok=True)
    shutil.copyfile(src, dst)


def main() -> None:
    copy(THEURGY / "item/list_filter.png", OUT / "item/list_filter.png")
    copy(THEURGY / "item/attribute_filter.png", OUT / "item/attribute_filter.png")

    crop(THEURGY / "gui/filters.png", OUT / "gui/sprites/list_filter_background.png", (0, 0, 214, 99))
    crop(THEURGY / "gui/filters.png", OUT / "gui/sprites/attribute_filter_background.png", (0, 99, 241, 184))

    widgets = THEURGY / "gui/widgets.png"
    icons = THEURGY / "gui/icons.png"

    for name, x, y, w, h in [
        ("button", 0, 0, 18, 18),
        ("button_hover", 18, 0, 18, 18),
        ("button_down", 36, 0, 18, 18),
        ("indicator", 0, 18, 18, 6),
        ("indicator_white", 18, 18, 18, 6),
        ("indicator_green", 36, 18, 18, 6),
        ("indicator_yellow", 54, 18, 18, 6),
        ("indicator_red", 72, 18, 18, 6),
    ]:
        crop(widgets, OUT / f"gui/sprites/{name}.png", (x, y, x + w, y + h))

    for name, x, y in [
        ("add", 0, 0),
        ("confirm", 0, 1),
        ("trash", 1, 0),
        ("accept_list", 9, 0),
        ("accept_list_or", 10, 0),
        ("accept_list_and", 11, 0),
        ("deny_list", 8, 0),
        ("deny_list_alt", 12, 0),
        ("respect_data_components", 13, 0),
        ("ignore_data_components", 14, 0),
        ("add_inverted", 4, 7),
    ]:
        crop(icons, OUT / f"gui/sprites/{name}.png", (x * 16, y * 16, x * 16 + 16, y * 16 + 16))

    crop(THEURGY / "gui/filters.png", OUT / "gui/sprites/attribute_filter_selection.png", (39, 122, 176, 140))
    crop(THEURGY / "gui/filters.png", OUT / "gui/sprites/attribute_filter_summary.png", (18, 154, 42, 178))


if __name__ == "__main__":
    main()

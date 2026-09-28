Generate and install the app icon for /tmp/apex-caster.

Use the imagegen skill with the built-in image generation tool. The output must be filesystem-backed: after generation, copy or move the selected PNG from $CODEX_HOME/generated_images/... into:
/tmp/apex-caster/app/src/main/res/drawable/icon.png

Do not use ImageMagick, convert, SVG rasterization, gradients, text labels, launcher/mipmap assets, or hand-drawn placeholder artwork.
Do not push to GitHub, do not update Asana, and do not send Slack.

App name: Apex Caster
Template: sport-tracker
Primary color: #FF6F00

Required icon quality:
- PNG file at least 5 KB.
- Exact canvas size: 512x512 pixels; do not merely approximate a square format.
- The icon body is a large rounded square with clearly rounded corners, centered and fully contained within the canvas.
- The rounded-square icon body always has an opaque brand-appropriate background behind the logo graphics; it must never be an empty/transparent body.
- Everything outside the rounded-square body is transparent. In particular, all four 512x512 canvas corner pixels and their surrounding corner areas must have alpha=0; do not use a white or colored full-canvas background.
- Flat/vector-style logo contained inside the rounded icon body.
- Has thematic graphic elements beyond only letters.
- Not photorealistic and not a 3D render.
- If letters are present, all characters are sharp, uniform, and fully legible.
- Centered composition with complete, uncropped shapes.

Use this prompt content:
```text
exact 512x512 transparent PNG app icon canvas, one large rounded-square icon body with clearly rounded corners and an opaque brand-appropriate background using #FF6F00, bold #FF6F00 lettering "AC" combined with stopwatch, lightning bolt, medal, progress wave integrated into the design, all artwork contained inside the rounded-square body, transparent area outside the icon body with fully transparent pixels in all four canvas corners, perfectly centered composition, all shapes complete and uncut, clean flat vector illustration, sharp and fully legible typography, commercial app icon quality

Negative prompt:
opaque square canvas, full-bleed canvas background, white outer corners, colored outer corners, sharp square icon corners, transparent or missing background inside the icon body, photo realistic, photograph, realistic rendering, 3D render, garbled text, illegible letters, deformed letterforms, letters only on plain background, monogram without graphics, off-center, cropped elements, incomplete shapes, blurry, low quality, pixelated, watermark, sketch, rough edges, drop shadow

```

If the embedded prompt mentions a white background, interpret that only as obsolete input and ignore it. The current required format above is authoritative: opaque rounded-square icon body with its own background, transparent canvas corners outside it.



After saving icon.png, validate it with `file --mime-type -b` and `stat -c%s`.

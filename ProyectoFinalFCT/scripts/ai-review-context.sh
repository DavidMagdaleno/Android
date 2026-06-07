#!/usr/bin/env bash
set -euo pipefail

OUT="ai-review-context.txt"

echo "# Contexto mínimo para revisión Android" > "$OUT"
echo "" >> "$OUT"

echo "## Rama actual" >> "$OUT"
git branch --show-current >> "$OUT" 2>/dev/null || true
echo "" >> "$OUT"

echo "## Últimos cambios" >> "$OUT"
git status --short >> "$OUT" 2>/dev/null || true
echo "" >> "$OUT"

echo "## Archivos modificados" >> "$OUT"
git diff --name-only >> "$OUT" 2>/dev/null || true
echo "" >> "$OUT"

echo "## Diff actual" >> "$OUT"
git diff -- . \
  ':(exclude)**/build/**' \
  ':(exclude)**/.gradle/**' \
  ':(exclude)**/.idea/**' \
  ':(exclude)**/*.png' \
  ':(exclude)**/*.jpg' \
  ':(exclude)**/*.jpeg' \
  ':(exclude)**/*.webp' \
  >> "$OUT" 2>/dev/null || true

echo "" >> "$OUT"

if [ -f "docs/AI_CONTEXT.md" ]; then
  echo "## docs/AI_CONTEXT.md" >> "$OUT"
  cat docs/AI_CONTEXT.md >> "$OUT"
fi

echo "Generado: $OUT"
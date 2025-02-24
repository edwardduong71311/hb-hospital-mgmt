#!/bin/bash
HOOKS_DIR="./hooks"
HOOK_TARGET_DIR=".git/hooks"

echo "Create target dir"
mkdir -p $HOOK_TARGET_DIR

echo "Copying"
cp -f $HOOKS_DIR/* $HOOK_TARGET_DIR/
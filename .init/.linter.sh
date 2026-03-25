#!/bin/bash
cd /home/kavia/workspace/code-generation/quality-defect-management-system-241571-241581/spring_boot_backend
./gradlew checkstyleMain
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi


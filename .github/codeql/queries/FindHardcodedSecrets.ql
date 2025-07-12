/**
 * @name Find hardcoded secrets
 * @description Detects hardcoded secrets in code
 * @kind problem
 * @problem.severity warning
 * @security-severity 8.0
 * @id java/hardcoded-secrets
 * @tags security
 */

import java

from Field field, StringLiteral literal
where
  field.getName().regexpMatch("(?i).*(api_?key|token|secret|password).*") and
  literal.getValue().regexpMatch("(?i).*(sk_.*|apikey_.*|token_.*|[a-zA-Z0-9+/=]{32,})")
select field, "Field: " + field.getName() + ", Value: " + literal.getValue()
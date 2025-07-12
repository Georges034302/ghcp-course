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

from StringLiteral literal
where 
  exists(Field field |
    // Match field name patterns
    field.getName().regexpMatch("(?i).*(api_?key|token|secret|password).*") and
    // Match field initialization
    literal = field.getInitializer() and
    // Match common secret patterns in value
    literal.getValue().regexpMatch("(?i).*(sk_.*|apikey_.*|token_.*|[a-zA-Z0-9+/=]{32,})")
  )
select 
  literal,
  "Hardcoded secret detected: '" + literal.getValue()
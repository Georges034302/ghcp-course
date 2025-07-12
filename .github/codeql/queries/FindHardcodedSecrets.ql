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

from StringLiteral literal, Field field
where 
  // Check field name contains sensitive keywords
  field.getName().regexpMatch("(?i).*(api_?key|token|secret|password).*") and
  // Check if literal initializes this field
  literal = field.getInitializer().(CompileTimeConstantExpr) and
  // Check if value matches secret pattern
  literal.getValue().regexpMatch("(?i).*(sk_.*|[a-zA-Z0-9]{32,})")
select literal, "Hardcoded secret detected in field '" + field.getName() + "'"
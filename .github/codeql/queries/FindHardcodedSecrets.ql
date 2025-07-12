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
where literal.getValue().regexpMatch("(?i).*(api[_-]?key|token|secret|password).*")
select literal, "Potential hardcoded secret detected"
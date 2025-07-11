/**
 * @name Find hardcoded secrets in Java
 * @description Detects string literals containing 'apiKey', 'token', 'secret', or 'password'
 * @kind problem
 * @problem.severity warning
 */

import java

class HardcodedSecretLiteral extends StringLiteral {
  HardcodedSecretLiteral() {
    this.getValue().matches("%apiKey%") or
    this.getValue().matches("%token%") or
    this.getValue().matches("%secret%") or
    this.getValue().matches("%password%")
  }
}

from HardcodedSecretLiteral secret, Method m
where secret.getEnclosingCallable() = m
select m, secret, "Hardcoded secret detected: " +
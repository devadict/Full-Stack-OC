import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function passwordValidator(): ValidatorFn {
  return (ctrl: AbstractControl): null | ValidationErrors => {
    const passwordRegex =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,}$/;
    const valid = passwordRegex.test(ctrl.value);
    return valid ? null : { invalidPassword: true };
  };
}
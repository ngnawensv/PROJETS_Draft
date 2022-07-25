import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { Subscription } from 'rxjs';
import { TokenStorageService } from '../auth/token-storage.service';

@Directive({
  selector: '[appHasRole]'
})
export class HasRoleDirective {

  private subscription: Subscription[] = [];
  // the role the user must have
  @Input() public appHasRole: Array<string>;

  /**
   * 
  * @param {ViewContainerRef} viewContainerRef -- the location where we need to render the templateRef
   * @param {TemplateRef<any>} templateRef -- the templateRef to be potentially rendered
   * @param {TokenStorageService} tokenStorage -- will give us access to the roles a user has 
   */
  constructor(
    private viewContainerRef: ViewContainerRef,
    private templateRef: TemplateRef<any>,
    private tokenStorage: TokenStorageService
  ) { }

  public ngOnInit(): void {
      const roles = this.tokenStorage.getRolesForCurrentUser();
      if (!roles) {
        // Remove element from DOM
        this.viewContainerRef.clear();
      }
      // user Role are checked by a Roles mention in DOM
      const idx = roles.findIndex((element) => this.appHasRole.indexOf(element) !== -1);
      if (idx < 0) {
        this.viewContainerRef.clear();
      } else {
        // appends the ref element to DOM
        this.viewContainerRef.createEmbeddedView(this.templateRef);
      }
     
  }

  /**
   * on destroy cancels the API if its fetching.
   */
  public ngOnDestroy(): void {
    this.subscription.forEach((subscription: Subscription) => subscription.unsubscribe());
  }

}

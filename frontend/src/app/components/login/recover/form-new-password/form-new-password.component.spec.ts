import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormNewPasswordComponent } from './form-new-password.component';

describe('FormNewPasswordComponent', () => {
  let component: FormNewPasswordComponent;
  let fixture: ComponentFixture<FormNewPasswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormNewPasswordComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FormNewPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

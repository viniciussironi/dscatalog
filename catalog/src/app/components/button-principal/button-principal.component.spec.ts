import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonPrincipalComponent } from './button-principal.component';

describe('ButtonPrincipalComponent', () => {
  let component: ButtonPrincipalComponent;
  let fixture: ComponentFixture<ButtonPrincipalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ButtonPrincipalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ButtonPrincipalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

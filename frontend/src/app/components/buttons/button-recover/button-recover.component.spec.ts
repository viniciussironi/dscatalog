import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonRecoverComponent } from './button-recover.component';

describe('ButtonRecoverComponent', () => {
  let component: ButtonRecoverComponent;
  let fixture: ComponentFixture<ButtonRecoverComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ButtonRecoverComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ButtonRecoverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

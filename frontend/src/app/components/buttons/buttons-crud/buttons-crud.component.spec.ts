import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonsCrudComponent } from './buttons-crud.component';

describe('ButtonsCrudComponent', () => {
  let component: ButtonsCrudComponent;
  let fixture: ComponentFixture<ButtonsCrudComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ButtonsCrudComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ButtonsCrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

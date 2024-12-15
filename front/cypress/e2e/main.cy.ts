describe('Main spec', () => {
  it('should display the main page', () => {
    cy.visit('http://localhost:4200');
    cy.contains('Yoga app').should('be.visible');
    cy.contains('Login').should('be.visible');
    cy.contains('Register').should('be.visible');
  });
});

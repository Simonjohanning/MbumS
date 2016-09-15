Runx2Dlx5Inhibitory_and = {
    'BMP2 = <>', 
    'TGFb1 = <>', 
    'Dlx5 = Msx2 && ~Runx2 && BMP2', 
    'Msx2 = BMP2 && TGFb1 && ~Dlx5', 
    'Runx2 = Dlx5 && ~Msx2 && ~TGFb1'
}
Runx2Dlx5Inhibitory_or = {
    'BMP2 = <>', 
    'TGFb1 = <>', 
    'Dlx5 = Msx2 || ~Runx2 || BMP2', 
    'Msx2 = BMP2 || TGFb1 || ~Dlx5', 
    'Runx2 = Dlx5 || ~Msx2 || ~TGFb1'
}
model_and = ExpressionsToOdefy(Runx2Dlx5Inhibitory_and)
simstruct_and = CreateSimstruct(model_and)
simstruct_and.type = 'hillcube'
model_or = ExpressionsToOdefy(Runx2Dlx5Inhibitory_or)
simstruct_or = CreateSimstruct(model_or)
simstruct_or.type = 'hillcube'
simstruct_and = SetInitialValue(simstruct_and, 'Dlx5', 0)
simstruct_and = SetInitialValue(simstruct_and, 'Msx2', 0)
simstruct_and = SetInitialValue(simstruct_and, 'Runx2', 0)
simstruct_or = SetInitialValue(simstruct_or, 'Dlx5', 0)
simstruct_or = SetInitialValue(simstruct_or, 'Msx2', 0)
simstruct_or = SetInitialValue(simstruct_or, 'Runx2', 0)

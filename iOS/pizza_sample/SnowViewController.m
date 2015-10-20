//
//  SnowViewController.m
//  pizza_sample
//
//  Created by YokoyamaDaisuke on 2015/08/13.
//  Copyright (c) 2015年 YokoyamaDaisuke. All rights reserved.
//

#import "SnowViewController.h"
#import "SnowScene.h"

@interface SnowViewController ()

@end

@implementation SnowViewController

- (void)viewDidLoad
{
    [super viewDidLoad];

    SKView * skView = (SKView *)self.view;
    
    SKScene * scene = [SnowScene sceneWithSize:skView.bounds.size];
    scene.scaleMode = SKSceneScaleModeAspectFill;
    
    [skView presentScene:scene];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end

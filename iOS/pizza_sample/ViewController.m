//
//  ViewController.m
//  pizza_sample
//
//  Created by YokoyamaDaisuke on 2015/08/11.
//  Copyright (c) 2015年 YokoyamaDaisuke. All rights reserved.
//

#import "ViewController.h"
#import "YLImageView.h"
#import "YLGIFImage.h"
#import "ActionSheet.h"
#import "Scene.h"
#import "QRReaderViewController.h"
#import "HoloViewController.h"
#import "FireworkViewController.h"
#import "SnowViewController.h"
#import "AlertView.h"
@import SpriteKit;

@interface ViewController () <QRReaderDelegate>

@property (nonatomic, weak) IBOutlet YLImageView *imageView;
@property (nonatomic, weak) IBOutlet UIButton *orderButton;
@property (strong, nonatomic) SKView *skView;

@end

@implementation ViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self.navigationController.navigationBar setBackgroundImage:[UIImage new] forBarMetrics:UIBarMetricsDefault];
    self.navigationController.navigationBar.shadowImage = [UIImage new];
    self.navigationController.navigationBar.translucent = YES;
    
    _imageView.image = [YLGIFImage imageNamed:@"pizza.gif"];
    
    // SKViewオブジェクトの生成と追加
    _skView = [[SKView alloc] initWithFrame:self.view.frame];
    //_skView.showsDrawCount = YES;
    //_skView.showsNodeCount = YES;
    //_skView.showsFPS = YES;
    
    _skView.allowsTransparency = YES;
    [self.view addSubview:_skView];
    [self.view insertSubview:_skView belowSubview:_orderButton];

    Scene *scene = [[Scene alloc] initWithSize:_imageView.bounds.size];
    scene.scaleMode = SKSceneScaleModeAspectFill;
    scene.backgroundColor = [SKColor clearColor];
    [_skView presentScene:scene];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    //NavigationBar非表示
    [self.navigationController setNavigationBarHidden:YES animated:YES];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    //NavigationBar表示
    [self.navigationController setNavigationBarHidden:NO animated:YES];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)pressOrderButton:(id)sender
{
    ActionSheet *action = [[ActionSheet alloc] initWithTitle:@"選択してください。" completion:^(NSInteger buttonIndex) {
        if (buttonIndex == 1) {
            QRReaderViewController *vc = [self.storyboard instantiateViewControllerWithIdentifier:@"QRReaderViewController"];
            vc.delegate = self;
            [self presentViewController:vc animated:YES completion:nil];
        } else if (buttonIndex == 2) {
            HoloViewController *vc = [self.storyboard instantiateViewControllerWithIdentifier:@"HoloViewController"];
            vc.holoFileName = @"pikachu.gif";
            [self.navigationController pushViewController:vc animated:YES];
        } else if (buttonIndex == 3) {
            FireworkViewController *vc = [self.storyboard instantiateViewControllerWithIdentifier:@"FireworkViewController"];
            [self.navigationController pushViewController:vc animated:YES];
        } else if (buttonIndex == 4) {
            SnowViewController *vc = [self.storyboard instantiateViewControllerWithIdentifier:@"SnowViewController"];
            [self.navigationController pushViewController:vc animated:YES];
        }
    } cancelButtonTitle:@"キャンセル" destructiveButtonTitle:nil otherButtonTitles:@"QRコードリーダー", @"ホログラム", @"夏", @"冬", nil];
    [action showInView:self.view];
}


#pragma mark - QRCodeReader Delegate Methods

- (void)reader:(QRReaderViewController *)reader didScanResult:(NSString *)result
{
    [self dismissViewControllerAnimated:YES completion:^{
        AlertView *alert = [[AlertView alloc] initWithTitle:@"URL検知しました" message:@"ホログラムを表示しますか？" completion:^(NSInteger buttonIndex) {
            if (buttonIndex == 1) {
                HoloViewController *vc = [self.storyboard instantiateViewControllerWithIdentifier:@"HoloViewController"];
                vc.holoFileName = @"pikachu.gif";
                [self.navigationController pushViewController:vc animated:YES];
            }
        } cancelButtonTitle:@"キャンセル" otherButtonTitles:@"OK", nil];
        [alert show];
    }];
}

@end
